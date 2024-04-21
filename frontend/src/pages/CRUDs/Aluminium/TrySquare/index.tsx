import { Route, Switch } from "react-router-dom";
import List from "./List";

const TrySquares = () => {
    return (
        <Switch>
          <Route path="/trySquares" exact>
            <List/>
          </Route>
        </Switch>
      );
}

export default TrySquares;