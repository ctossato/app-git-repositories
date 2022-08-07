package me.ctossato.githubrepos.domain

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.annotations.JsonAdapter

import com.google.gson.annotations.SerializedName
import java.lang.reflect.Type


data class GitRepository (
    val id : String,
    val name: String,
    val description: String,
    @SerializedName("stargazers_count")
    val stars: Int,
    @SerializedName("language")
    val languages: String,
    @SerializedName("owner")
    @JsonAdapter(AvatarDeserializer::class)
    val ownerAvatar: String
        )

class AvatarDeserializer : JsonDeserializer<String?> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): String {
        return json.asJsonObject["avatar_url"].asString
    }
}