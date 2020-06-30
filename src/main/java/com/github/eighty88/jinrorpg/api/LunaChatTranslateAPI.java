package com.github.eighty88.jinrorpg.api;

import com.github.ucchyocean.lc.LunaChat;
import com.github.ucchyocean.lc.japanize.JapanizeType;

public class LunaChatTranslateAPI {
    public static String Translate(String str, TranslateType Type) {
        if(Type.equals(TranslateType.NONE)) {
            return LunaChat.getInstance().getLunaChatAPI().japanize(str, JapanizeType.NONE);
        } else if(Type.equals(TranslateType.KANA)) {
            return LunaChat.getInstance().getLunaChatAPI().japanize(str, JapanizeType.KANA);
        } else if(Type.equals(TranslateType.GOOGLE_IME)) {
            return LunaChat.getInstance().getLunaChatAPI().japanize(str, JapanizeType.GOOGLE_IME);
        }
        return null;
    }
}
