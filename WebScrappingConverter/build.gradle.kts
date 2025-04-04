plugins {
    application
    java
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jsoup:jsoup:1.15.4")
    implementation("technology.tabula:tabula:1.0.3")
    implementation("org.apache.pdfbox:pdfbox:2.0.27")
}

application {
    mainClass.set("org.example.Main")  // Ajuste conforme o seu código
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "org.example.Main"
    }
}

tasks.register<Jar>("fatJar") {
    archiveBaseName.set("WebScrappingConverter")
    archiveVersion.set("1.0")
    archiveClassifier.set("all")

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    manifest {
        attributes["Main-Class"] = "org.example.Main"
    }

    from(
        configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) }
    ) {
        exclude("META-INF/*.SF", "META-INF/*.DSA", "META-INF/*.RSA")  // Remove arquivos de assinatura
    }

    with(tasks.jar.get() as CopySpec)
}


tasks.withType<Test> {
    useJUnitPlatform()
}
