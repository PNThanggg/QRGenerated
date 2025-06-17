package apps.qr.generated.core.scheme

data class Geo(
    var q: String = "", var points: MutableList<String> = mutableListOf()
) : Schema<Geo> {

    companion object {
        const val GEO = "geo"
        const val Q = "q"

        fun parse(geoInfoCode: String?): Geo {
            val geo = Geo()
            geo.parseSchema(geoInfoCode)
            return geo
        }
    }

    override fun parseSchema(code: String?): Geo {
        if (code == null || !code.trim().lowercase().startsWith(GEO)) {
            throw IllegalArgumentException("this is not a geo info code: $code")
        }
        val pointsArray = code.trim().lowercase().replace("$GEO:", "").split(",")
        if (pointsArray.isNotEmpty()) {
            points.addAll(pointsArray)
        }
        return this
    }

    override fun generateString(): String {
        return "$GEO:${points.joinToString(",")}${if (q.isEmpty()) "" else "?$Q=$q"}"
    }

    override fun toString(): String {
        return generateString()
    }
}