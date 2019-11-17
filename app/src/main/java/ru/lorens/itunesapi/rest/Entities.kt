package ru.lorens.itunesapi.rest

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class ResultAlbum(
    @Expose @SerializedName("resultCount") val resultCount: Int,
    @Expose @SerializedName("results") val results: List<Album>
)

data class Album(
    @Expose @SerializedName("wrapperType") val wrapperType: String,
    @Expose @SerializedName("collectionType") val collectionType: String,
    @Expose @SerializedName("artistId") val artistId: Long,
    @Expose @SerializedName("collectionId") val collectionId: Long,
    @Expose @SerializedName("artistName") val artistName: String,
    @Expose @SerializedName("collectionName") val collectionName: String,
    @Expose @SerializedName("collectionCensoredName") val collectionCensoredName: String,
    @Expose @SerializedName("artistViewUrl") val artistViewUrl: String,
    @Expose @SerializedName("collectionViewUrl") val collectionViewUrl: String,
    @Expose @SerializedName("artworkUrl60") val artworkUrl60: String,
    @Expose @SerializedName("artworkUrl100") val artworkUrl100: String,
    @Expose @SerializedName("collectionPrice") val collectionPrice: Double,
    @Expose @SerializedName("collectionExplicitness") val collectionExplicitness: String,
    @Expose @SerializedName("trackCount") val trackCount: Int,
    @Expose @SerializedName("copyright") val copyright: String,
    @Expose @SerializedName("country") val country: String,
    @Expose @SerializedName("currency") val currency: String,
    @Expose @SerializedName("releaseDate") val releaseDate: String,
    @Expose @SerializedName("primaryGenreName") val primaryGenreName: String
) : Serializable

data class ResultSong(
    @Expose @SerializedName("resultCount") val resultCount: Int,
    @Expose @SerializedName("results") val results: List<Song>
)

data class Song(
    @Expose @SerializedName("trackId") val trackId: Long,
    @Expose @SerializedName("trackName") val trackName: String,
    @Expose @SerializedName("previewUrl") val previewUrl: String,
    @Expose @SerializedName("artworkUrl60") val artworkUrl60: String,
    @Expose @SerializedName("trackNumber") val trackNumber: Int,
    @Expose @SerializedName("primaryGenreName") val primaryGenreName: String,
    @Expose @SerializedName("trackTimeMillis") val trackTimeMillis: Long
)