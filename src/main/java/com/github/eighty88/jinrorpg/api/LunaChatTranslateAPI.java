package com.github.eighty88.jinrorpg.api;

import com.github.ucchyocean.lc3.LunaChat;
import com.github.ucchyocean.lc3.japanize.JapanizeType;

public class LunaChatTranslateAPI {
    public static String Translate(String str, TranslateType Type) {
        switch (Type) {
            case NONE:
                return LunaChat.getAPI().japanize(str, JapanizeType.NONE);
            case KANA:
                return LunaChat.getAPI().japanize(str, JapanizeType.KANA);
            case GOOGLE_IME:
                return LunaChat.getAPI().japanize(str, JapanizeType.GOOGLE_IME);
        }
        return null;
    }
}
