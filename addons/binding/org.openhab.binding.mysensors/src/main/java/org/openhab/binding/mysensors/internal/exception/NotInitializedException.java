/**
 * Copyright (c) 2010-2017 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.mysensors.internal.exception;

/**
 * Exception occurs if the state of a thing is not yet initialized.
 *
 * @author Tim Oberföll
 *
 */
public class NotInitializedException extends Exception {
    private static final long serialVersionUID = -1441354134423131361L;

    private String message;

    public NotInitializedException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
