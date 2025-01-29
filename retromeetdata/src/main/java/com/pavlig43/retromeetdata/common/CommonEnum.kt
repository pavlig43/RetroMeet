package com.pavlig43.retromeetdata.common

import kotlinx.serialization.Serializable

@Serializable
enum class Orientation {
    HOMOSEXUAL,
    LESBIAN,
    TRADITIONAL,
    BISEXUAL,
    ASEXUAL
}

@Serializable
enum class Gender {
    Male,
    Female
}

@Serializable
enum class Religion {
    Islam,
    Christianity,
    Judaism,
    Hinduism,
    Buddhism,
    Atheism,
    Agnosticism,
    Other
}

@Serializable
enum class EyeColor {
    Blue,
    Green,
    Brown,
    Hazel,
    Gray,
    Amber,
    Other
}

@Serializable
enum class HairColor {
    Blonde,
    Brown,
    LIGHT_BROWN,
    Black,
    Red,
    Gray,
    White,
    Other
}

@Serializable
enum class Education {
    HighSchool,
    High,
    Bachelors,
    Masters,
    Doctorate,
    Other
}

@Serializable
enum class Smoking {
    Never,
    Sometimes,
    Often,
    Regularly
}

@Serializable
enum class Drinking {
    Never,
    Socially,
    Often,
    Regularly
}

@Serializable
enum class MaritalStatus {
    Single,
    Married,
    IN_ROMANTIC,
    IN_SEARCH,
    ALL_DIFFICULT,
    Widowed
}

@Serializable
enum class Pet {
    Dog,
    Cat,
    Bird,
    Fish,
    Reptile,
    Other
}

@Serializable
enum class MusicGenre {
    Pop,
    Rock,
    Jazz,
    Classical,
    HipHop,
    Country,
    Electronic,
    Other
}
