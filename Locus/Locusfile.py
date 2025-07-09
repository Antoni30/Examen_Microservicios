from locust import HttpUser, task, between
from faker import Faker
import random
import uuid

fake = Faker()

class MicroserviciosPorGateway(HttpUser):
    wait_time = between(1, 2)
    host = "http://localhost:8000"  # Usa el API Gateway

    # Variables de clase compartidas entre usuarios
    dispositivos_creados = []
    id_counter = 1

    # === RecolectorDatosPaciente ===

    @task
    def crear_dispositivo(self):
        # Accede y actualiza el contador global de clase
        id_dispositivo = f"d{self.__class__.id_counter}"
        self.__class__.id_counter += 1

        payload = {
            "idDispositivo": id_dispositivo,
            "tipoDispositivo": fake.random_element(elements=("Monitor", "Sensor", "Otro")),
            "ubicacion": fake.city(),
            "estado": fake.random_element(elements=("Activo", "Desactivado"))
        }

        response = self.client.post("/conjunta/2p/dispositivos", json=payload)
        if response.status_code == 200 or response.status_code == 201:
            self.__class__.dispositivos_creados.append(id_dispositivo)  # Guardar ID exitoso

    @task
    def crear_signo_vital(self):
        if not self.__class__.dispositivos_creados:
            return  # No crear signos si no hay dispositivos

        dispositivo_id = random.choice(self.__class__.dispositivos_creados)

        tipo_signo = fake.random_element(elements=["frecuencia cardiaca", "presion arterial", "oxigeno"])

        # Generar valor según tipo
        if tipo_signo == "frecuencia cardiaca":
            valor = random.randint(60, 100)  # en bpm
        elif tipo_signo == "presion arterial":
            # Podrías usar un string tipo "120/80" si así lo requiere tu API
            valor = random.randint(90, 140)  # valor sistólico o promedio si es entero
        else:  # oxígeno
            valor = random.randint(90, 100)  # en %

        payload = {
            "idDispositivo": dispositivo_id,
            "tipoSigno": tipo_signo,
            "valor": valor,
            "fechaMedicion": "2025-07-07T15:30:00-05:00"
        }

        self.client.post("/conjunta/2p/vital-signs", json=payload)


        # === ms-notificaciones ===

        @task
        def obtener_notificaciones(self):
            self.client.get("/notificacion/api/notificaciones")

        @task
        def obtener_notificaciones_generales(self):
            self.client.get("/notificacion/api/notificacionesGenerales")

        @task
        def buscar_notificacion_general_por_id(self):
            id_falso = str(uuid.uuid4())  # Cambia por uno válido si quieres respuesta 200
            self.client.get(f"/notificacion/api/notificacionesGenerales/{id_falso}")
