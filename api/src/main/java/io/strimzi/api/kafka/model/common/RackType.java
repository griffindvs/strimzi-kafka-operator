/*
 * Copyright Strimzi authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.strimzi.api.kafka.model.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum RackType {
    NODE_LABEL,
    ENVVAR;

    @JsonCreator
    public static RackType forValue(String value) {
        switch (value) {
            case "node-label":
                return NODE_LABEL;
            case "envvar":
                return ENVVAR;
            default:
                return null;
        }
    }

    @JsonValue
    public String toValue() {
        switch (this) {
            case NODE_LABEL:
                return "node-label";
            case ENVVAR:
                return "envvar";
            default:
                return null;
        }
    }
}
