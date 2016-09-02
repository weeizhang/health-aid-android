package none.healthaide.usercase;

import none.healthaide.model.Case;

public interface NewCaseView {
    Case getCase();

    void loadSuccess(long id);
}
