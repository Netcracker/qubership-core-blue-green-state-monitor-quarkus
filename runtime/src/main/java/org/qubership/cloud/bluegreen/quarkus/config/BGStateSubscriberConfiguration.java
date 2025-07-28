package org.qubership.cloud.bluegreen.quarkus.config;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import org.qubership.cloud.bluegreen.api.model.BlueGreenState;
import org.qubership.cloud.bluegreen.api.service.BlueGreenStatePublisher;

@ApplicationScoped
public class BGStateSubscriberConfiguration {
    public static final String BG_STATE_SYSTEM_PROPERTY_KEY = "bgState";
    private static final Logger log = Logger.getLogger(BGStateSubscriberConfiguration.class);

    @Inject
    BlueGreenStatePublisher blueGreenStatePublisher;

    void onStart(@Observes StartupEvent ev) {
        if (blueGreenStatePublisher != null) {
            log.info("Subscribe to BlueGreenState change event => store BlueGreenState to the System property");
            blueGreenStatePublisher.subscribe(this::setBGStateToSystemProperties);
        } else {
            log.warn("Cannot get BlueGreenStatePublisher bean -> skip subscription");
        }
    }

    private synchronized void setBGStateToSystemProperties(BlueGreenState blueGreenState) {
        System.setProperty(BG_STATE_SYSTEM_PROPERTY_KEY, blueGreenState.getCurrent().getState().getName());
    }
}
