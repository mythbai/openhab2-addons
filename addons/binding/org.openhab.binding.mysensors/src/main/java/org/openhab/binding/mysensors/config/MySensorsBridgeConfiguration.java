/**
 * Copyright (c) 2010-2017 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.mysensors.config;

/**
 * Parameters used for bridge configuration.
 *
 * @author Tim Oberföll
 *
 */
public class MySensorsBridgeConfiguration {
    public String serialPort; // serial port the gateway is attached to
    public String ipAddress; // ip address the gateway is attached to
    public Integer tcpPort; // tcp port the gateway is running at
    public Integer sendDelay; // delay at which messages are send from the internal queue to the MySensors network
    public Integer baudRate; // baud rate used to connect the serial port
    public Boolean imperial; // should nodes send imperial or metric values?
    public Boolean skipStartupCheck; // should the startup check of the bridge at boot skipped?
    public Boolean enableNetworkSanCheck; // network sanity check enabled?
    public Integer sanityCheckerInterval; // determines interval to start NetworkSanityCheck
    public Integer sanCheckConnectionFailAttempts; // connection will wait this number of attempts before disconnecting
    public boolean sanCheckSendHeartbeat; // network sanity checker will also send heartbeats to all known nodes
    public Integer sanCheckSendHeartbeatFailAttempts; // disconnect nodes that fail to answer to heartbeat request

    @Override
    public String toString() {
        return "MySensorsBridgeConfiguration [serialPort=" + serialPort + ", ipAddress=" + ipAddress + ", tcpPort="
                + tcpPort + ", sendDelay=" + sendDelay + ", baudRate=" + baudRate + ", imperial=" + imperial
                + ", skipStartupCheck=" + skipStartupCheck + ", enableNetworkSanCheck=" + enableNetworkSanCheck
                + ", sanityCheckerInterval=" + sanityCheckerInterval + ", sanCheckConnectionFailAttempts="
                + sanCheckConnectionFailAttempts + ", sanCheckSendHeartbeat=" + sanCheckSendHeartbeat
                + ", sanCheckSendHeartbeatFailAttempts=" + sanCheckSendHeartbeatFailAttempts + "]";
    }

}
