/*
 * Copyright 2019 dc-square GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hivemq.extensions.packets.publish;

import com.hivemq.annotations.NotNull;
import com.hivemq.extension.sdk.api.packets.general.Qos;
import com.hivemq.extension.sdk.api.packets.general.UserProperties;
import com.hivemq.extension.sdk.api.packets.publish.PayloadFormatIndicator;
import com.hivemq.extension.sdk.api.packets.publish.PublishPacket;
import com.hivemq.mqtt.message.connect.MqttWillPublish;
import com.hivemq.mqtt.message.publish.PUBLISH;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author Florian Limpöck
 * @since 4.0.0
 */
public class WillPublishPacketImpl implements PublishPacket {

    private final @NotNull MqttWillPublish publish;

    public WillPublishPacketImpl(final @NotNull MqttWillPublish publish) {
        this.publish = publish;
    }

    @Override
    public boolean getDupFlag() {
        return false;
    }

    @Override
    public @NotNull Qos getQos() {
        return Qos.valueOf(publish.getQos().name());
    }

    @Override
    public boolean getRetain() {
        return publish.isRetain();
    }

    @Override
    public @NotNull String getTopic() {
        return publish.getTopic();
    }

    @Override
    public int getPacketId() {
        return 0;
    }

    @Override
    public @NotNull Optional<PayloadFormatIndicator> getPayloadFormatIndicator() {
        if (publish.getPayloadFormatIndicator() == null) {
            return Optional.empty();
        } else {
            return Optional.of(PayloadFormatIndicator.valueOf(publish.getPayloadFormatIndicator().name()));
        }
    }

    @Override
    public @NotNull Optional<Long> getMessageExpiryInterval() {
        if (publish.getMessageExpiryInterval() == PUBLISH.MESSAGE_EXPIRY_INTERVAL_NOT_SET) {
            return Optional.empty();
        } else {
            return Optional.of(publish.getMessageExpiryInterval());
        }
    }

    @Override
    public @NotNull Optional<String> getResponseTopic() {
        return Optional.ofNullable(publish.getResponseTopic());
    }

    @Override
    public @NotNull Optional<ByteBuffer> getCorrelationData() {
        final byte[] correlationData = publish.getCorrelationData();
        if (correlationData == null) {
            return Optional.empty();
        }
        return Optional.of(ByteBuffer.wrap(correlationData).asReadOnlyBuffer());
    }

    @Override
    public @NotNull List<Integer> getSubscriptionIdentifiers() {
        return Collections.emptyList();
    }

    @Override
    public @NotNull Optional<String> getContentType() {
        return Optional.ofNullable(publish.getContentType());
    }

    @Override
    public @NotNull Optional<ByteBuffer> getPayload() {
        final byte[] payload = publish.getPayload();
        if (payload == null) {
            return Optional.empty();
        }
        return Optional.of(ByteBuffer.wrap(payload).asReadOnlyBuffer());
    }

    @Override
    public @NotNull UserProperties getUserProperties() {
        return publish.getUserProperties().getPluginUserProperties();
    }
}
