/*
 * Copyright Strimzi authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.strimzi.api.kafka.model.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.strimzi.crdgenerator.annotations.Description;
import io.strimzi.crdgenerator.annotations.DescriptionFile;
import io.strimzi.crdgenerator.annotations.Example;
import io.sundr.builder.annotations.Buildable;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * Representation of the rack configuration.
 */
@DescriptionFile
@Buildable(
        editableEnabled = false,
        builderPackage = Constants.FABRIC8_KUBERNETES_API
)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"type", "topologyKey"})
@EqualsAndHashCode
@ToString
public class Rack implements UnknownPropertyPreserving {
    private RackType type;
    private String topologyKey;
    private Map<String, Object> additionalProperties;

    public Rack() {
        this.type = RackType.ENVVAR;
    }

    public Rack(String topologyKey) {
        this.type = RackType.NODE_LABEL;
        this.topologyKey = topologyKey;
    }

    @Description("Type of the rack configuration. " +
            "The supported types are as follows: \n\n" +
            "* `node-label` type configures the rack using the node label with the provided topologyKey.\n" +
            "* `envvar` type configures the rack using the `STRIMZI_RACK` environment variable.\n")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public RackType getType() {
        return type;
    }

    @Description("A key that matches labels assigned to the Kubernetes cluster nodes. " +
            "The value of the label is used to set a broker's `broker.rack` config, and the `client.rack` config for Kafka Connect or MirrorMaker 2.")
    @Example("topology.kubernetes.io/zone")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getTopologyKey() {
        return topologyKey;
    }

    @Override
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties != null ? this.additionalProperties : Map.of();
    }

    @Override
    public void setAdditionalProperty(String name, Object value) {
        if (this.additionalProperties == null) {
            this.additionalProperties = new HashMap<>(2);
        }
        this.additionalProperties.put(name, value);
    }

    @Description("Returns true if the rack configuration requires the usage of an init container " +
            "for fetching the rack information.")
    public Boolean useRackInitContainer() {
        return this.type == RackType.NODE_LABEL;
    }
}
