package io.horizontalsystems.lightningwallet.modules.settings

import androidx.lifecycle.ViewModel

class MainSettingsPresenter(
        val view: MainSettingsModule.IMainSettingsView,
        val router: MainSettingsModule.IMainSettingsRouter,
        private val interactor: MainSettingsModule.IMainSettingsInteractor)
    : ViewModel(), MainSettingsModule.IMainSettingsViewDelegate, MainSettingsModule.IMainSettingsInteractorDelegate {

    private val helper = MainSettingsHelper()

    override fun viewDidLoad() {
        view.setBaseCurrency(helper.displayName(interactor.baseCurrency))
        view.setLanguage(interactor.currentLanguageDisplayName)
        view.setLightMode(interactor.lightMode)
        view.setAppVersion(interactor.appVersion)
    }

    override fun didTapSecurity() {
        router.showSecuritySettings()
    }

    override fun didTapBaseCurrency() {
        router.showBaseCurrencySettings()
    }

    override fun didTapLanguage() {
        router.showLanguageSettings()
    }

    override fun didSwitchLightMode(lightMode: Boolean) {
        interactor.lightMode = lightMode
        router.reloadAppInterface()
    }

    override fun didTapAbout() {
        router.showAbout()
    }

    override fun didTapCompanyLogo() {
        // router.openLink(interactor.companyWebPageLink)
    }

    override fun didTapReportProblem() {
        router.showReportProblem()
    }

    override fun didTapTellFriends() {
        // router.showShareApp(interactor.appWebPageLink)
    }

    override fun didTapNotifications() {
        router.showNotifications()
    }

    // IMainSettingsInteractorDelegate

    override fun didUpdateAllBackedUp(allBackedUp: Boolean) {
        view.setBackedUp(allBackedUp)
    }

    override fun didUpdateBaseCurrency() {
        view.setBaseCurrency(helper.displayName(interactor.baseCurrency))
    }

    // ViewModel

    override fun onCleared() {
        interactor.clear()
    }

}
