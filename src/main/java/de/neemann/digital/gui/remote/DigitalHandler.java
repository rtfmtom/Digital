/*
 * Copyright (c) 2016 Helmut Neemann
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digital.gui.remote;

import de.neemann.digital.gui.DigitalRemoteInterface;
import de.neemann.digital.lang.Lang;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Handler to control the simulator.
 * The handler simply interprets the incoming request and calls the suited method
 * of the {@link DigitalRemoteInterface} which is implemented by the {@link de.neemann.digital.gui.Main} class.
 */
public class DigitalHandler implements HandlerInterface {
    private final DigitalRemoteInterface digitalRemoteInterface;

    /**
     * Creates a new server instance
     *
     * @param digitalRemoteInterface the remote interface which is used by the server
     */
    public DigitalHandler(DigitalRemoteInterface digitalRemoteInterface) {
        this.digitalRemoteInterface = digitalRemoteInterface;
    }

    @Override
    public String handleRequest(String request) {
        List<String> args = new ArrayList<>();
        String[] parts = request.split(":");
        String command = parts[0];
        for (String part : parts) {
            if (part.equals(parts[0])) {
                continue;
            }
            args.add(part);
        }

        try {
            String ret = handle(command.toLowerCase(), args);
            if (ret != null)
                return "ok:"+ret;
            else
                return "ok";
        } catch (RemoteException e) {
            return e.getMessage();
        }
    }

    private String handle(String command, List<String> args) throws RemoteException {

        if (args.size() > 2 && !command.equals("output")) {
            StringBuilder errArgs = new StringBuilder();
            for (String arg : args) {
                errArgs.append(arg).append(" ");
            }
            throw new RemoteException("Invalid arguments: " + errArgs);
        }

        if ((args.size() > 1 && !command.equals("input") && !command.equals("output")) || (args.size() > 2)) {
            StringBuilder errArgs = new StringBuilder();
            for (String arg : args) {
                errArgs.append(arg).append(" ");
            }
            throw new RemoteException("Invalid arguments: " + errArgs);
        }

        switch (command) {
            case "step":
                return digitalRemoteInterface.doSingleStep();
            case "clock":
                return digitalRemoteInterface.doClock();
            case "start":
                digitalRemoteInterface.start(new File(args.get(0)), false);
                return null;
            case "debug":
                digitalRemoteInterface.debug(new File(args.get(0)), false);
                return null;
            case "run":
                return digitalRemoteInterface.runToBreak();
            case "stop":
                digitalRemoteInterface.stop();
                return null;
            case "measure":
                return digitalRemoteInterface.measure();
            case "output":
                if (args.size() == 1) {
                    return digitalRemoteInterface.output(args.get(0));
                } else if (args.size() == 2) {
                    return digitalRemoteInterface.output(args.get(0) + ":" + args.get(1));
                } else {
                    throw new RemoteException("output requires 1 or 2 arguments");
                }
            case "input":
                return digitalRemoteInterface.input(args.get(0), args.get(1));
            default:
                throw new RemoteException(Lang.get("msg_remoteUnknownCommand", command));
        }
    }
}
