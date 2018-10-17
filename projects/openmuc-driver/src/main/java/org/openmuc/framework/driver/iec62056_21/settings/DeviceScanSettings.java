/*
 * Copyright 2016-18 ISC Konstanz
 *
 * This file is part of OpenIEC62056-21.
 * For more information visit https://github.com/isc-konstanz/OpenIEC62056-21.
 *
 * OpenIEC62056-21 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * OpenIEC62056-21 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OpenIEC62056-21.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.openmuc.framework.driver.iec62056_21.settings;

import org.openmuc.framework.config.PreferenceType;
import org.openmuc.framework.config.Preferences;

import gnu.io.SerialPort;

@SuppressWarnings("deprecation")
public class DeviceScanSettings extends Preferences {

    public static final PreferenceType TYPE = PreferenceType.SETTINGS_SCAN_DEVICE;

    @Option
    private String serialPort;

    @Option
    private int timeout = 5000;

    @Option
    private int baudrate = 300;

	@Option
    private int databits = SerialPort.DATABITS_7;

    @Option
    private int stopbits = SerialPort.STOPBITS_1;

    @Option
    private int parity = SerialPort.PARITY_EVEN;

    @Override
    public PreferenceType getPreferenceType() {
        return TYPE;
    }

    public String getSerialPort() {
        return serialPort;
    }

    public int getTimeout() {
    	return timeout;
    }

    public int getBaudrate() {
        return baudrate;
    }

    public int getDatabits() {
        return databits;
    }

    public int getStopbits() {
        return stopbits;
    }

    public int getParity() {
        return parity;
    }

}
