import { Route, Switch } from "react-router-dom";
import List from "./List";

const EdgeBandings = () => {
    return (
        <Switch>
          <Route path="/edgeBandings" exact>
            <List/>
          </Route>
        </Switch>
      );
}

export default EdgeBandings;