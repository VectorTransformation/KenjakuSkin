plugins {
    `java-library`
}

group = "kenjakuSkin"
version = "1.0.1"
val javaVersion = 16
val filePath = ""

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.17-R0.1-SNAPSHOT")
}

tasks {
    compileJava {
        options.release = javaVersion
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }
    processResources {
        filteringCharset = Charsets.UTF_8.name()
        expand(
            "name" to project.name,
            "version" to version,
            "description" to "본 적 없는 걸 보고 싶지 않나?\n재밌을 것 같았던 것이 정말로 재밌는지 확인해 보고 싶지 않나?\n그것이 바로 삶이 아니겠는가?",
            "author" to "Kenjaku",
            "website" to "https://namu.wiki/w/%EC%BC%84%EC%9E%90%EC%BF%A0",
            "apiVersion" to "1.17"
        )
    }
    jar {
        archiveFileName = "${project.name}${if (version == "") version else "-${version}"}.jar"
        if (filePath != "") {
            destinationDirectory.set(File(filePath))
        }
    }
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(javaVersion)
}