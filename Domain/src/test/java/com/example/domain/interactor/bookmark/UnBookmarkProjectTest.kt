package com.example.domain.interactor.bookmark

import com.example.domain.executor.PostExecutionThread
import com.example.domain.repository.ProjectsRepository
import com.example.domain.test.ProjectDataFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class UnBookmarkProjectTest {

    private lateinit var unBookmarkProject: UnBookmarkProject
    @Mock lateinit var projectsRepository: ProjectsRepository
    @Mock lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        unBookmarkProject = UnBookmarkProject(projectsRepository, postExecutionThread)
    }

    @Test
    fun unBookmarkProjectCompletes() {
        stubUnBookmarkProject(Completable.complete())
        val testObserver = unBookmarkProject.buildUseCaseCompletable(
                UnBookmarkProject.Params.forProject(ProjectDataFactory.randomUuid())
        ).test()

        testObserver.assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun unBookmarkProjectThrowsException() {
        unBookmarkProject.buildUseCaseCompletable().test()
    }

    private fun stubUnBookmarkProject(completable: Completable) {
        whenever(projectsRepository.unbookmarkProject(any()))
                .thenReturn(completable)
    }
}