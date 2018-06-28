import com.example.data.model.ProjectEntity
import com.example.remote.ProjectsRemoteImpl
import com.example.remote.mapper.ProjectsResponseModelMapper
import com.example.remote.model.ProjectModel
import com.example.remote.model.ProjectsResponseModel
import com.example.remote.service.GithubTrendingService
import com.example.remote.test.factory.ProjectDataFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import io.reactivex.ObservableSource
import org.junit.Test

class ProjectsRemoteImplTest {

    private val service =  mock<GithubTrendingService>()
    private val mapper = mock<ProjectsResponseModelMapper>()
    private val remote = ProjectsRemoteImpl(service, mapper)

    @Test
    fun getProjectsCompletes() {
        stubGithubTrendingServicesSearchRepositories(Observable.just(
                ProjectDataFactory.makeProjectsResponse()))

        stubProjectsResponseModelMapperMapFromModel(any(), ProjectDataFactory.makeProjectEntity())

        val testObserver = remote.getProjects().test()
        testObserver.assertComplete()
    }

    @Test
    fun getProjectsCallsServer() {
        stubGithubTrendingServicesSearchRepositories(Observable.just(
                ProjectDataFactory.makeProjectsResponse()))

        stubProjectsResponseModelMapperMapFromModel(any(), ProjectDataFactory.makeProjectEntity())

         remote.getProjects().test()
         verify(service).searchRepositories(any(), any(), any())
    }

    @Test
    fun getProjectsCallsServerWithCorectParameters() {
        stubGithubTrendingServicesSearchRepositories(Observable.just(
                ProjectDataFactory.makeProjectsResponse()))

        stubProjectsResponseModelMapperMapFromModel(any(), ProjectDataFactory.makeProjectEntity())

        remote.getProjects().test()
        verify(service).searchRepositories("language:kotlin", "stars", "desc")
    }

    @Test
    fun getProjectsReturnsData() {
        val response = ProjectDataFactory.makeProjectsResponse()
        stubGithubTrendingServicesSearchRepositories(Observable.just(response))

        val entities = mutableListOf<ProjectEntity>()
        response.items.forEach {
            val entity= ProjectDataFactory.makeProjectEntity()
            entities.add(entity)
            stubProjectsResponseModelMapperMapFromModel(it, entity)
        }

        val testObserver = remote.getProjects().test()
        testObserver.assertValue(entities)
    }

    private fun stubGithubTrendingServicesSearchRepositories(observable: Observable<ProjectsResponseModel>) {
        whenever(service.searchRepositories(any(), any(), any()))
                .thenReturn(observable)
    }

    private fun stubProjectsResponseModelMapperMapFromModel(model: ProjectModel,
                                                            entity: ProjectEntity) {
        whenever(mapper.mapFromModel(model))
                .thenReturn(entity)

    }
}