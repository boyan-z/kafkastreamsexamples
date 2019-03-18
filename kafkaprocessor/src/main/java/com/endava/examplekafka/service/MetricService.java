package com.endava.examplekafka.service;

import com.endava.examplekafka.config.Streams;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Log4j2
public class MetricService implements ApplicationListener<ContextRefreshedEvent> {

    private static final int PAUSE_BETWEEN_SENDING = 1000 * 30;

    private final InteractiveQueryService interactiveQueryService;
    private final TaskExecutor taskExecutor;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        taskExecutor.execute(() -> {
            while (true) {
                log.info("Writing metrics");
                StringBuilder sb = new StringBuilder("Metrics\n");
                showCounts().entrySet().forEach(e -> sb.append(e.toString()).append("\n"));
                try {
                    Files.write(Paths.get("output.txt"), sb.toString().getBytes());
                } catch (IOException e) {
                    log.error(e);
                }
                try {
                    Thread.sleep(PAUSE_BETWEEN_SENDING);
                } catch (InterruptedException e) {
                    log.error(e);
                }
            }
        });
    }

    private Map<String, Long> showCounts() {
        ReadOnlyKeyValueStore<String, Long> store = interactiveQueryService.getQueryableStore(Streams.MAT, QueryableStoreTypes.keyValueStore());

        Map<String, Long> map = new HashMap<>();

        if (store != null) {
            store.all().forEachRemaining(stringLongKeyValue -> map.put(stringLongKeyValue.key, stringLongKeyValue.value));
        }

        return map;
    }
}
