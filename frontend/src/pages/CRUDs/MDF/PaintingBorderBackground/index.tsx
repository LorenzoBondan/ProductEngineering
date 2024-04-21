import { Route, Switch } from "react-router-dom";
import List from "./List";

const PaintingBorderBackgrounds = () => {
    return (
        <Switch>
          <Route path="/paintingBorderBackgrounds" exact>
            <List/>
          </Route>
        </Switch>
      );
}

export default PaintingBorderBackgrounds;