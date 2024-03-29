package com.example.reply.ui

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.reply.data.Email
import com.example.reply.data.MailboxType
import com.example.reply.ui.utils.ReplyContentType
import com.example.reply.ui.utils.ReplyNavigationType
@Composable
fun ReplyApp(
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
) {
    val viewModel: ReplyViewModel = viewModel()
    val contentType: ReplyContentType
    val replyUiState = viewModel.uiState.collectAsState().value
    val navigationType: ReplyNavigationType
    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            contentType = ReplyContentType.LIST_ONLY
            navigationType = ReplyNavigationType.BOTTOM_NAVIGATION
        }
        WindowWidthSizeClass.Medium -> {
            contentType = ReplyContentType.LIST_ONLY
            navigationType = ReplyNavigationType.NAVIGATION_RAIL
        }
        WindowWidthSizeClass.Expanded -> {
            contentType = ReplyContentType.LIST_AND_DETAIL
            navigationType = ReplyNavigationType.PERMANENT_NAVIGATION_DRAWER
        }
        else -> {
            contentType = ReplyContentType.LIST_ONLY
            navigationType = ReplyNavigationType.BOTTOM_NAVIGATION
        }
    }
    ReplyHomeScreen(
        replyUiState = replyUiState,
        onTabPressed = { mailboxType: MailboxType ->
            viewModel.updateCurrentMailbox(mailboxType = mailboxType)
            viewModel.resetHomeScreenStates()
        },
        onEmailCardPressed = { email: Email ->
            viewModel.updateDetailsScreenStates(
                email = email
            )
        },
        onDetailScreenBackPressed = {
            viewModel.resetHomeScreenStates()
        },
        modifier = modifier,
        navigationType = navigationType,
        contentType = contentType
    )
}
