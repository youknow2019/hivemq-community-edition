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

package com.hivemq.mqtt.message.reason;

import com.hivemq.annotations.NotNull;
import com.hivemq.annotations.Nullable;
import com.hivemq.extension.sdk.api.packets.publish.AckReasonCode;

/**
 * MQTT Reason Codes that can be used in PUBACK packets according to the MQTT 5 specification.
 *
 * @author Silvio Giebl
 */
public enum Mqtt5PubAckReasonCode implements Mqtt5ReasonCode {

    SUCCESS(MqttCommonReasonCode.SUCCESS),
    NO_MATCHING_SUBSCRIBERS(MqttCommonReasonCode.NO_MATCHING_SUBSCRIBERS),
    UNSPECIFIED_ERROR(MqttCommonReasonCode.UNSPECIFIED_ERROR),
    IMPLEMENTATION_SPECIFIC_ERROR(MqttCommonReasonCode.IMPLEMENTATION_SPECIFIC_ERROR),
    NOT_AUTHORIZED(MqttCommonReasonCode.NOT_AUTHORIZED),
    TOPIC_NAME_INVALID(MqttCommonReasonCode.TOPIC_NAME_INVALID),
    PACKET_IDENTIFIER_IN_USE(MqttCommonReasonCode.PACKET_IDENTIFIER_IN_USE),
    QUOTA_EXCEEDED(MqttCommonReasonCode.QUOTA_EXCEEDED),
    PAYLOAD_FORMAT_INVALID(MqttCommonReasonCode.PAYLOAD_FORMAT_INVALID);

    private static final @NotNull Mqtt5PubAckReasonCode[] VALUES = values();

    private final int code;

    Mqtt5PubAckReasonCode(final int code) {
        this.code = code;
    }

    Mqtt5PubAckReasonCode(final @NotNull MqttCommonReasonCode reasonCode) {
        this(reasonCode.getCode());
    }

    @Override
    public int getCode() {
        return code;
    }

    /**
     * Returns the PUBACK Reason Code belonging to the given byte code.
     *
     * @param code the byte code.
     * @return the PUBACK Reason Code belonging to the given byte code or <code>null</code> if the byte code is not a
     * valid PUBACK Reason Code.
     */
    public static @Nullable Mqtt5PubAckReasonCode fromCode(final int code) {
        for (final Mqtt5PubAckReasonCode reasonCode : VALUES) {
            if (reasonCode.code == code) {
                return reasonCode;
            }
        }
        return null;
    }

    @Override
    public boolean canBeSentByClient() {
        return this != NO_MATCHING_SUBSCRIBERS;
    }

    @NotNull
    public static Mqtt5PubAckReasonCode fromAckReasonCode(@NotNull final AckReasonCode reasonCode) {
        switch (reasonCode) {
            case SUCCESS:
                return Mqtt5PubAckReasonCode.SUCCESS;
            case NO_MATCHING_SUBSCRIBERS:
                return Mqtt5PubAckReasonCode.NO_MATCHING_SUBSCRIBERS;
            case UNSPECIFIED_ERROR:
                return Mqtt5PubAckReasonCode.UNSPECIFIED_ERROR;
            case IMPLEMENTATION_SPECIFIC_ERROR:
                return Mqtt5PubAckReasonCode.IMPLEMENTATION_SPECIFIC_ERROR;
            case NOT_AUTHORIZED:
                return Mqtt5PubAckReasonCode.NOT_AUTHORIZED;
            case TOPIC_NAME_INVALID:
                return Mqtt5PubAckReasonCode.TOPIC_NAME_INVALID;
            case PACKET_IDENTIFIER_IN_USE:
                return Mqtt5PubAckReasonCode.PACKET_IDENTIFIER_IN_USE;
            case QUOTA_EXCEEDED:
                return Mqtt5PubAckReasonCode.QUOTA_EXCEEDED;
            case PAYLOAD_FORMAT_INVALID:
                return Mqtt5PubAckReasonCode.PAYLOAD_FORMAT_INVALID;
            default:
                throw new IllegalArgumentException("Unknown AckReason code");
        }
    }
}
