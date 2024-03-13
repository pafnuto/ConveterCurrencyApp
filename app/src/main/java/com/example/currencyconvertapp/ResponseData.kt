import com.google.gson.annotations.SerializedName

data class ResponseData(
    val date: String,
    @SerializedName("usd", alternate = ["rub", "eur", "jpy","cny","ils"])
    val conversionResult: String)





