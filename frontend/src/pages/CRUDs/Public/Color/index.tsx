import { Route, Switch } from "react-router-dom";
import List from "./List";

const Colors = () => {
    return (
        <Switch>
          <Route path="/colors" exact>
            <List/>
          </Route>
        </Switch>
      );
}

export default Colors;