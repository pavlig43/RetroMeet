package com.pavlig43.retromeetdata.utils.database

import androidx.room.TypeConverter
import com.pavlig43.retromeetdata.common.Education
import com.pavlig43.retromeetdata.common.EyeColor
import com.pavlig43.retromeetdata.common.Gender
import com.pavlig43.retromeetdata.common.HairColor
import com.pavlig43.retromeetdata.common.MusicGenre
import com.pavlig43.retromeetdata.common.Orientation
import com.pavlig43.retromeetdata.common.Pet
import com.pavlig43.retromeetdata.common.Religion
import java.lang.Enum.valueOf

class EnumListConverters {

    class GenderConverter {
        @TypeConverter
        fun fromEnumList(value: List<Gender>?): String? {
            return value?.joinToString(",") { it.name }
        }

        @TypeConverter
        fun toEnumList(value: String?): List<Gender>? {
            return value?.split(",")?.map { Gender.valueOf(it) }
        }
    }

    class OrientationConverter {
        @TypeConverter
        fun fromEnumList(value: List<Orientation>?): String? {
            return value?.joinToString(",") { it.name }
        }

        @TypeConverter
        fun toEnumList(value: String?): List<Orientation>? {
            return value?.split(",")?.map { Orientation.valueOf(it) }
        }
    }

    class ReligionConverter {
        @TypeConverter
        fun fromEnumList(value: List<Religion>?): String? {
            return value?.joinToString(",") { it.name }
        }

        @TypeConverter
        fun toEnumList(value: String?): List<Religion>? {
            return value?.split(",")?.map { Religion.valueOf(it) }
        }
    }

    class EyeColorConverter {
        @TypeConverter
        fun fromEnumList(value: List<EyeColor>?): String? {
            return value?.joinToString(",") { it.name }
        }

        @TypeConverter
        fun toEnumList(value: String?): List<EyeColor>? {
            return value?.split(",")?.map { EyeColor.valueOf(it) }
        }
    }

    class HairColorConverter {
        @TypeConverter
        fun fromEnumList(value: List<HairColor>?): String? {
            return value?.joinToString(",") { it.name }
        }

        @TypeConverter
        fun toEnumList(value: String?): List<HairColor>? {
            return value?.split(",")?.map { HairColor.valueOf(it) }
        }
    }

    class EducationConverter {
        @TypeConverter
        fun fromEnumList(value: List<Education>?): String? {
            return value?.joinToString(",") { it.name }
        }

        @TypeConverter
        fun toEnumList(value: String?): List<Education>? {
            return value?.split(",")?.map { Education.valueOf(it) }
        }
    }

    class PetConverter {
        @TypeConverter
        fun fromEnumList(value: List<Pet>?): String? {
            return value?.joinToString(",") { it.name }
        }

        @TypeConverter
        fun toEnumList(value: String?): List<Pet>? {
            return value?.split(",")?.map { Pet.valueOf(it) }
        }
    }

    class MusicGenreConverter {
        @TypeConverter
        fun fromEnumList(value: List<MusicGenre>?): String? {
            return value?.joinToString(",") { it.name }
        }

        @TypeConverter
        fun toEnumList(value: String?): List<MusicGenre>? {
            return value?.split(",")?.map { MusicGenre.valueOf(it) }
        }
    }

}












