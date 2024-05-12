import { Route, Switch } from "react-router-dom";
import List from "./List";

const Guides = () => {
    return (
        <Switch>
          <Route path="/guides" exact>
            <List/>
          </Route>
        </Switch>
      );
}

export default Guides;