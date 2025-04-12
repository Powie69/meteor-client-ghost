import { getMcVersion } from "./mc_version.js"

getMcVersion().then((version) => {
	console.log("version=" + version)
})