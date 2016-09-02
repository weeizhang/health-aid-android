package none.healthaide.usercase;

public class NewCasePresenter implements CaseInteractor.Callback {

    private CaseInteractor caseInteractor;
    private NewCaseView newCaseView;

    public NewCasePresenter(CaseInteractor caseInteractor, NewCaseView newCaseView) {
        this.caseInteractor = caseInteractor;
        this.newCaseView = newCaseView;
    }

    public void submitNewCase() {
        caseInteractor.storeNewCase(newCaseView.getCase(), this);
    }

    @Override
    public void onLoadNewCaseSuccess(long id) {
        newCaseView.loadSuccess(id);
    }

    @Override
    public void onLoadNewCaseFailed() {

    }
}
