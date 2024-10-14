package top.voemp.sppatch.hook

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClassOrNull
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.Log
import com.github.kyuubiran.ezxhelper.ObjectHelper.Companion.objectHelper
import com.github.kyuubiran.ezxhelper.finders.ConstructorFinder.`-Static`.constructorFinder
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder

object LyricGetterAPI : BaseHook() {
    override fun init() {
        val clazz = loadClassOrNull("cn.lyric.getter.api.API")
        clazz?.constructorFinder()?.first()?.createHook {
            before { hookParam ->
                if ((hookParam.thisObject.objectHelper().getObjectOrNullAs<Int>("API_VERSION")
                        ?: 0) >= 6
                ) {
                    clazz.methodFinder().first { name == "sendLyric" }.createHook {
                        before { hookParam ->
                            val extra = (hookParam.args[1]).objectHelper()
                                .getObjectOrNullAs<HashMap<String, Any>>("extra")
                            extra?.put("packageName", "com.salt.music")
                            extra?.put("customIcon", false)
                            extra?.put("base64Icon", "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")
                        }
                    }
                }
            }
        }
        Log.i("LyricGetterAPI hook init")
    }
}