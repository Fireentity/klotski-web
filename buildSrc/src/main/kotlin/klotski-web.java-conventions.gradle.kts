plugins {
    java
}

apply {
    plugin("java")
}

project.the<SourceSetContainer>()["main"].java {
    srcDir("src/")
}

project.the<SourceSetContainer>()["main"].resources {
    srcDir("resources/")
}

project.the<SourceSetContainer>()["test"].java {
    srcDir("test/")
}