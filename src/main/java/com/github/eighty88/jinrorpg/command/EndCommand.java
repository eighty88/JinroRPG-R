package com.github.eighty88.jinrorpg.command;

import com.github.eighty88.jinrorpg.JinroRPG;
import com.github.eighty88.jinrorpg.controller.GameController;

public class EndCommand {
    public static boolean onCommand() {
        if(JinroRPG.isStarted) {
            GameController.End(null);
            return true;
        }
        return false;
    }
}
