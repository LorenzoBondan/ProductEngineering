import { Route, Switch } from "react-router-dom";
import List from "./List";

const Paintings = () => {
    return (
        <Switch>
          <Route path="/paintings" exact>
            <List/>
          </Route>
        </Switch>
      );
}

export default Paintings;