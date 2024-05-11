import { Route, Switch } from "react-router-dom";
import List from "./List";

const PaintingSons = () => {
    return (
        <Switch>
          <Route path="/paintingsons" exact>
            <List/>
          </Route>
        </Switch>
      );
}

export default PaintingSons;