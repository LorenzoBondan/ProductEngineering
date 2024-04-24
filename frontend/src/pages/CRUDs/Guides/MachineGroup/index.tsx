import { Route, Switch } from "react-router-dom";
import List from "./List";

const MachineGroups = () => {
    return (
        <Switch>
          <Route path="/machineGroups" exact>
            <List/>
          </Route>
        </Switch>
      );
}

export default MachineGroups;