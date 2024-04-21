import { Route, Switch } from "react-router-dom";
import List from "./List";

const Screws = () => {
    return (
        <Switch>
          <Route path="/screws" exact>
            <List/>
          </Route>
        </Switch>
      );
}

export default Screws;