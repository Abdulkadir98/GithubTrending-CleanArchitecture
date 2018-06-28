package com.example.cache.dao

import android.arch.persistence.room.*
import com.example.cache.db.ProjectConstants.DELETE_PROJECTS
import com.example.cache.db.ProjectConstants.QUERY_BOOKMARKED_PROJECTS
import com.example.cache.db.ProjectConstants.QUERY_PROJECTS
import com.example.cache.db.ProjectConstants.QUERY_UPDATE_BOOKMARK_STATUS
import com.example.cache.model.CachedProject
import io.reactivex.Flowable

@Dao
abstract class CachedProjectsDao {

    @Query(QUERY_PROJECTS)
    abstract fun getProjects() : Flowable<List<CachedProject>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertProjects(projects: List<CachedProject>)

    @Query(DELETE_PROJECTS)
    abstract fun deleteProjects()

    @Query(QUERY_BOOKMARKED_PROJECTS)
    abstract fun getBookmarkedProjects(): Flowable<List<CachedProject>>

    @Query(QUERY_UPDATE_BOOKMARK_STATUS)
    abstract fun setBookmarkStatus(isBookmarked: Boolean,
                                   projectId: String)
}