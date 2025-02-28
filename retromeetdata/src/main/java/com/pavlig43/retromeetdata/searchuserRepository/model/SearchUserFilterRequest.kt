package com.pavlig43.retromeetdata.searchuserRepository.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.pavlig43.retromeetdata.common.Drinking
import com.pavlig43.retromeetdata.common.Education
import com.pavlig43.retromeetdata.common.EyeColor
import com.pavlig43.retromeetdata.common.Gender
import com.pavlig43.retromeetdata.common.HairColor
import com.pavlig43.retromeetdata.common.MaritalStatus
import com.pavlig43.retromeetdata.common.MusicGenre
import com.pavlig43.retromeetdata.common.Orientation
import com.pavlig43.retromeetdata.common.Pet
import com.pavlig43.retromeetdata.common.Religion
import com.pavlig43.retromeetdata.common.Smoking
import com.pavlig43.retromeetdata.utils.database.EnumListConverters
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "search_filter")
@TypeConverters(
    EnumListConverters.GenderConverter::class,
    EnumListConverters.OrientationConverter::class,
    EnumListConverters.ReligionConverter::class,
    EnumListConverters.EyeColorConverter::class,
    EnumListConverters.HairColorConverter::class,
    EnumListConverters.EducationConverter::class,
    EnumListConverters.PetConverter::class,
    EnumListConverters.MusicGenreConverter::class
)
@Serializable
data class SearchUserFilterRequest(

    @ColumnInfo("city")
    @SerialName("city")
    val city: String? = null,

    @ColumnInfo("name")
    @SerialName("name")
    val name: String? = null,

    @ColumnInfo("gender")
    @SerialName("gender")
    val gender: List<Gender>? = null,

    @ColumnInfo("orientation")
    @SerialName("orientation")
    val orientation: List<Orientation>? = null,

    @Embedded(prefix = "date_of_birth_range_")
    @SerialName("date_of_birth_range")
    val dateOfBirthRange: DateOfBirthRange? = null,

    @ColumnInfo("children")
    @SerialName("children")
    val children: Boolean? = null,

    @ColumnInfo("religion")
    @SerialName("religion")
    val religion: List<Religion>? = null,

    @SerialName("weight")
    @Embedded(prefix = "weight_")
    val weight: WeightRange? = null,

    @Embedded(prefix = "height_")
    @SerialName("height")
    val height: HeightRange? = null,

    @ColumnInfo("eye_color")
    @SerialName("eye_color")
    val eyeColor: List<EyeColor>? = null,

    @ColumnInfo("hair_color")
    @SerialName("hair_color")
    val hairColor: List<HairColor>? = null,

    @ColumnInfo("education")
    @SerialName("education")
    val education: List<Education>? = null,

    @ColumnInfo("smoking")
    @SerialName("smoking")
    val smoking: Smoking? = null,

    @ColumnInfo("drinking")
    @SerialName("drinking")
    val drinking: Drinking? = null,

    @ColumnInfo("marital_status")
    @SerialName("marital_status")
    val maritalStatus: MaritalStatus? = null,

    @ColumnInfo("pets")
    @SerialName("pets")
    val pets: List<Pet>? = null,

    @ColumnInfo("favorite_music_genres")
    @SerialName("favorite_music_genres")
    val favoriteMusicGenres: List<MusicGenre>? = null,

    @ColumnInfo("is_online")
    @SerialName("is_online")
    val isOnline: Boolean,

    @PrimaryKey
    @ColumnInfo("id")
    @SerialName("id")
    val id: Int = 1,

)




@Serializable
data class DateOfBirthRange(
    @SerialName("from")
    val from: Long?,
    @SerialName("to")
    val to: Long?
)
@Serializable
data class WeightRange(
    @SerialName("from")
    val from: Int?,
    @SerialName("to")
    val to: Int?
)
@Serializable
data class HeightRange(
    @SerialName("from")
    val from: Int?,
    @SerialName("to")
    val to: Int?
)
