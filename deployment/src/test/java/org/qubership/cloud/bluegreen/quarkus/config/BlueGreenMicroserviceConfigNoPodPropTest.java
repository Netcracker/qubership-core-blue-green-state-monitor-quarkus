package org.qubership.cloud.bluegreen.quarkus.config;


import org.qubership.cloud.bluegreen.api.service.MicroserviceMutexService;
import org.qubership.cloud.bluegreen.quarkus.deployment.BlueGreenStateMonitorProcessor;
import io.quarkus.test.QuarkusUnitTest;
import jakarta.inject.Inject;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

class BlueGreenMicroserviceConfigNoPodPropTest {

    @RegisterExtension
    static QuarkusUnitTest runner = new QuarkusUnitTest()
            .setArchiveProducer(() -> ShrinkWrap.create(JavaArchive.class)
                    .addClass(BlueGreenStateMonitorProcessor.class)
                    .addAsResource("application-microservice-no-pod.properties", "application.properties")
            );

    @Inject
    MicroserviceMutexService microserviceMutexService;

    @Test
    void testMicroserviceMutexService() {
        Assertions.assertNotNull(microserviceMutexService);
    }
}
