package com.timeaccounting.web.command;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class CommandContainer {

    public static final Logger LOG = Logger.getLogger(CommandContainer.class);

    private static Map<String, Command> commands = new HashMap<>();

    static {
        commands.put("loginCommand", new LoginCommand());
        commands.put("categoryCommand", new CategoryCommand());
        commands.put("activityCommand", new ActivityCommand());
        commands.put("pinActivityCommand", new PinActivityCommand());
        commands.put("userCommand", new UserCommand());
        commands.put("activityReportCommand", new ActivityReportCommand());
        commands.put("usersReportCommand", new UsersReportCommand());
        commands.put("userSetActivityCommand", new UserSetActivityCommand());
        commands.put("userDeleteActivityCommand", new UserDeleteActivityCommand());
        commands.put("userSetTimeCommand", new UserSetTimeCommand());
        commands.put("userCurrentActivitiesCommand", new UserCurrentActivitiesCommand());
        commands.put("logoutCommand", new LogoutCommand());
        commands.put("noCommand", new NoCommand());
    }

    public static Command get(String commandName) {
        if (commandName == null || !commands.containsKey(commandName)) {
            LOG.trace("Command not found, name --> " + commandName);
            return commands.get("noCommand");
        }
        return commands.get(commandName);
    }

    public static Boolean getAllCommands(String commandName) {
        if (commandName == null || !commands.containsKey(commandName)) {
            return false;
        }
        return true;
    }

}
