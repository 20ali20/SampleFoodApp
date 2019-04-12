package alimojarrad.chownowtest

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*




open class BaseModel : Serializable {
    @SerializedName("id")
    @Expose
    var id: String? = null

    var internalUuid: String? = UUID.randomUUID().toString()

    override fun equals(other: Any?): Boolean {
        val o = other as? BaseModel

        return if (o != null) {
            when {
                o.id!= null -> o.id == id
                o.internalUuid != null -> o.internalUuid == o.internalUuid
                else -> false
            }
        } else {
            false
        }

    }
}
