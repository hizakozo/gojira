package com.example.gojiraapi.domain.project

import arrow.core.NonEmptyList
import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class ProjectSpec : DescribeSpec({
    describe("Project") {
        it("should create a project") {
            val project = Project.create("Project 1").shouldBeRight()
            project.projectName.value shouldBe "Project 1"
        }
        it("should not create a project with an empty name") {
            val project = Project.create("")
            project shouldBeLeft ProjectDomainError.ProjectCreateError(
                NonEmptyList(
                    InvalidProjectName(
                        input = "",
                        reason = "cannot be empty"
                    ),
                    emptyList()
                )
            )
        }
        it("should not create a project with a name longer than 50 characters") {
            val project = Project.create("a".repeat(51))
            project shouldBeLeft ProjectDomainError.ProjectCreateError(
                NonEmptyList(
                    InvalidProjectName(
                        input = "a".repeat(51),
                        reason = "cannot be longer than 50 characters"
                    ),
                    emptyList()
                )
            )
        }
    }
})
