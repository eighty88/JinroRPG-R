package com.github.eighty88.jinrorpg.command;

import com.github.eighty88.jinrorpg.JinroRPG;
import com.github.eighty88.jinrorpg.controller.GameController;
import com.github.eighty88.jinrorpg.roles.RoleType;

public class EndCommand {
    public static boolean onCommand() {
        if(JinroRPG.isStarted) {
            GameController.End(RoleType.NONE);
            return true;
        }
        return false;
    }
}
