import { Route, Switch } from "react-router-dom";
import List from "./List";

const Sheets = () => {
    return (
        <Switch>
          <Route path="/sheets" exact>
            <List/>
          </Route>
        </Switch>
      );
}

export default Sheets;