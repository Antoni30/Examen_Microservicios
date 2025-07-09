package ec.edu.espe.msanalizadorsalud.service;

import ec.edu.espe.msanalizadorsalud.entity.MedicalAlert;
import ec.edu.espe.msanalizadorsalud.repository.MedicalAlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReporteSaludService {

    private final MedicalAlertRepository alertRepository;

    // Cada 24h - reporte de métricas
    @Scheduled(cron = "0 0 0 * * *") // cada día a la medianoche
    public void generarReporteDiario() {
        LocalDateTime desde = LocalDateTime.now().minusDays(1);
        List<MedicalAlert> ultimas24h = alertRepository.findByFechaAfter(desde);

        Map<String, List<Integer>> agrupadosPorTipo = ultimas24h.stream()
                .collect(Collectors.groupingBy(
                        MedicalAlert::getTipo,
                        Collectors.mapping(MedicalAlert::getValor, Collectors.toList())
                ));

        agrupadosPorTipo.forEach((tipo, valores) -> {
            Double promedio = valores.stream().mapToInt(v -> v).average().orElse(0);
            Integer max = valores.stream().max(Integer::compare).orElse(null);
            Integer min = valores.stream().min(Integer::compare).orElse(null);

            // Aquí podrías enviar el evento por RabbitMQ
            System.out.printf("📊 Reporte diario para %s → Promedio: %.2f, Máximo: %d, Mínimo: %d%n",
                    tipo, promedio, max, min);

            // emitir evento DailyReportGenerated
        });
    }

    // Cada 6h - verificación de dispositivos sin actividad
    @Scheduled(cron = "0 0 */6 * * *") // cada 6 horas
    public void verificarDispositivosInactivos() {
        LocalDateTime corte = LocalDateTime.now().minusDays(1);

        // Obtener todos los dispositivos con alertas en las últimas 24h
        List<String> dispositivosActivos = alertRepository.findByFechaAfter(corte).stream()
                .map(MedicalAlert::getDispositivoId)
                .distinct()
                .toList();

        // Obtener todos los dispositivos registrados (desde BD o algún microservicio)
        List<String> todosDispositivos = alertRepository.findAll().stream()
                .map(MedicalAlert::getDispositivoId)
                .distinct()
                .toList();

        List<String> inactivos = todosDispositivos.stream()
                .filter(d -> !dispositivosActivos.contains(d))
                .toList();

        inactivos.forEach(id -> {
            System.out.println("⚠️ Dispositivo inactivo detectado: " + id);
            // Emitir evento DeviceOfflineAlert
        });
    }
}
