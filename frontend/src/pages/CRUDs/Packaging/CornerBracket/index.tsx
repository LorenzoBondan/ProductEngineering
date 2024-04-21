import { Route, Switch } from "react-router-dom";
import List from "./List";

const CornerBrackets = () => {
    return (
        <Switch>
          <Route path="/cornerBrackets" exact>
            <List/>
          </Route>
        </Switch>
      );
}

export default CornerBrackets;