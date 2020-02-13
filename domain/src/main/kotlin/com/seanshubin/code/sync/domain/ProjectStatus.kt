package com.seanshubin.code.sync.domain

enum class ProjectStatus {
    IN_SYNC,
    PENDING_EDITS,
    CREATE_IN_GITHUB,
    CLONE_LOCALLY,
    LOCAL_COMMITS,
    REMOTE_COMMITS
}
