/*
 * Copyright 2016-20 ISC Konstanz
 *
 * This file is part of OpenSmartMeter.
 * For more information visit https://github.com/isc-konstanz/OpenSmartMeter.
 *
 * OpenSmartMeter is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * OpenSmartMeter is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OpenSmartMeter.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.openmuc.framework.driver.smartmeter.iec;

import java.io.IOException;
import java.util.List;

import org.openmuc.framework.config.ArgumentSyntaxException;
import org.openmuc.framework.config.ScanException;
import org.openmuc.framework.driver.ChannelScanner;
import org.openmuc.framework.driver.smartmeter.SmartMeterDevice;
import org.openmuc.framework.driver.smartmeter.configs.Configurations;
import org.openmuc.framework.driver.smartmeter.configs.ObisChannel;
import org.openmuc.framework.driver.spi.ConnectionException;
import org.openmuc.framework.driver.spi.RecordsReceivedListener;
import org.openmuc.iec62056.Iec62056;
import org.openmuc.iec62056.Iec62056Builder;

public class ModeDListener extends SmartMeterDevice {

    private final Configurations configs;

    private Iec62056Builder builder;
    private Iec62056 connection;

    private IecListener listener;

    public ModeDListener(Configurations configs) {
    	this.configs = configs;
    }

    @Override
    protected void onCreate() {
        builder = Iec62056Builder.create(configs.getSerialPort())
                .setDeviceAddress(configs.getAddress())
        		.setPassword(configs.getPassword())
                .setMsgStartChars(configs.getMsgStartChars())
                .enableBaudRateHandshake(configs.hasHandshake())
                .setBaudRateChangeDelay(configs.getBaudRateChangeDelay())
                .setBaudRate(configs.getBaudRate())
				.setTimeout(configs.getTimeout());
    }

	@Override
    protected ChannelScanner onCreateScanner(String settings) 
            throws UnsupportedOperationException, ArgumentSyntaxException, ScanException, ConnectionException {
        
		return new IecScanner(listener.dataSets);
    }

	@Override
    protected void onConnect() throws ArgumentSyntaxException, ConnectionException {
        try {
        	listener = new IecListener(this);
			connection = builder.build();
			connection.listen(listener);
			
		} catch (IOException e) {
			throw new ConnectionException(e);
		}
    }

	@Override
	protected void onDisconnect() {
    	connection.close();
	}

    @Override
    public void onStartListening(List<ObisChannel> channels, RecordsReceivedListener listener)
            throws UnsupportedOperationException, ConnectionException {
        
    	this.listener.register(listener, channels);
    }

    @Override
    public Object onRead(List<ObisChannel> channels, Object containerListHandle, String samplingGroup)
            throws UnsupportedOperationException, ConnectionException {
        
    	this.listener.parseDataSets(channels);
        return null;
    }

}
