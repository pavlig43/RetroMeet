package com.pavlig43.features.common.enumui.model

import androidx.annotation.StringRes
import com.pavlig43.features.R

interface EnumUi {
    @get:StringRes
    val translate: Int
    @get:StringRes
    val description: Int
}

enum class OrientationUi(override val description: Int = R.string.orientation) : EnumUi {
    NULL{ override val translate: Int = R.string.not_important},
    HOMOSEXUAL { override val translate: Int = R.string.homosexual },
    LESBIAN { override val translate: Int = R.string.lesbian },
    TRADITIONAL { override val translate: Int = R.string.traditional },
    BISEXUAL { override val translate: Int = R.string.bisexual },
    ASEXUAL { override val translate: Int = R.string.asexual }
}

enum class GenderUi(override val description: Int = R.string.gender) : EnumUi {
    NULL{ override val translate: Int = R.string.not_important},
    Male { override val translate: Int = R.string.male },
    Female { override val translate: Int = R.string.female }
}

enum class ReligionUi(override val description: Int = R.string.religion) : EnumUi {
    NULL{ override val translate: Int = R.string.not_important},
    Islam { override val translate: Int = R.string.islam },
    Christianity { override val translate: Int = R.string.christianity },
    Judaism { override val translate: Int = R.string.judaism },
    Hinduism { override val translate: Int = R.string.hinduism },
    Buddhism { override val translate: Int = R.string.buddhism },
    Atheism { override val translate: Int = R.string.atheism },
    Agnosticism { override val translate: Int = R.string.agnosticism },
    Other { override val translate: Int = R.string.other }
}

enum class EyeColorUi(override val description: Int = R.string.eye_color) : EnumUi {
    NULL{ override val translate: Int = R.string.not_important},
    Blue { override val translate: Int = R.string.blue },
    Green { override val translate: Int = R.string.green },
    Brown { override val translate: Int = R.string.brown },
    Hazel { override val translate: Int = R.string.hazel },
    Gray { override val translate: Int = R.string.gray },
    Amber { override val translate: Int = R.string.amber },
    Other { override val translate: Int = R.string.other }
}

enum class HairColorUi(override val description: Int = R.string.hair_color) : EnumUi {
    NULL{ override val translate: Int = R.string.not_important},
    Blonde { override val translate: Int = R.string.blonde },
    LIGHT_BROWN { override val translate: Int = R.string.light_brown_hair },
    Brown { override val translate: Int = R.string.brown_hair },
    Black { override val translate: Int = R.string.black_hair },
    Red { override val translate: Int = R.string.red_hair },
    Gray { override val translate: Int = R.string.gray_hair },
    White { override val translate: Int = R.string.white_hair },
    Other { override val translate: Int = R.string.other }
}

enum class EducationUi(override val description: Int = R.string.education) : EnumUi {
    NULL{ override val translate: Int = R.string.not_important},
    High{ override val translate: Int = R.string.high},
    HighSchool { override val translate: Int = R.string.high_school },
    Bachelors { override val translate: Int = R.string.bachelors },
    Masters { override val translate: Int = R.string.masters },
    Doctorate { override val translate: Int = R.string.doctorate },
    Other { override val translate: Int = R.string.other }
}

enum class SmokingUi(override val description: Int = R.string.smoking) : EnumUi {
    NULL{ override val translate: Int = R.string.not_important},
    Never { override val translate: Int = R.string.never },
    Sometimes { override val translate: Int = R.string.sometimes },
    Often { override val translate: Int = R.string.often },
    Regularly { override val translate: Int = R.string.regularly }
}

enum class DrinkingUi(override val description: Int = R.string.alcohol) : EnumUi {
    NULL{ override val translate: Int = R.string.not_important},
    Never { override val translate: Int = R.string.never },
    Socially { override val translate: Int = R.string.socially_drinking },
    Often { override val translate: Int = R.string.often},
    Regularly { override val translate: Int = R.string.regularly }
}

enum class MaritalStatusUi(override val description: Int = R.string.marital_status) : EnumUi {
    IN_ROMANTIC{override val translate = R.string.in_romantic},
    IN_SEARCH{override val translate = R.string.in_search},
    ALL_DIFFICULT{override val translate = R.string.all_difficult},
    NULL{ override val translate: Int = R.string.not_important},
    Single { override val translate: Int = R.string.single },
    Married { override val translate: Int = R.string.married },
    Widowed { override val translate: Int = R.string.widowed }
}

enum class PetUi(override val description: Int = R.string.pet) : EnumUi {
    Dog { override val translate: Int = R.string.dog },
    Cat { override val translate: Int = R.string.cat },
    Bird { override val translate: Int = R.string.bird },
    Fish { override val translate: Int = R.string.fishes },
    Reptile { override val translate: Int = R.string.reptile },
    Other { override val translate: Int = R.string.other }
}

enum class MusicGenreUi(override val description: Int = R.string.music) : EnumUi {
    Pop { override val translate: Int = R.string.pop },
    Rock { override val translate: Int = R.string.rock },
    Jazz { override val translate: Int = R.string.jazz },
    Classical { override val translate: Int = R.string.classical },
    HipHop { override val translate: Int = R.string.hip_hop },
    Country { override val translate: Int = R.string.country },
    Electronic { override val translate: Int = R.string.electronic },
    Other { override val translate: Int = R.string.other }
}
enum class IsHasChildrenUi(override val description: Int = R.string.children): EnumUi{
    NULL{ override val translate: Int = R.string.not_important},
    YES{ override val translate: Int = R.string.yes},
    NO{ override val translate: Int = R.string.no},
}
