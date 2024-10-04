# BrandBlock

A simple Fabric Mod for blocking/filtering Minecraft clients (JUST SERVER SIDE!).

The config file (should be named `clientblock.json` inside your Fabric mods settings folder and comments removed)
```jsonc
{
  "action": "block", // "block" or "allow" (block by default)
  "clients": [
    "fabric"
  ], // Array of clients to block or allow (based in action mode)
  "kickMessage": "Custom kick message" // The client is not allowed!
}
```

Here you can copy the template without comments :)
```json
{
  "action": "block",
  "clients": [
    "fabric"
  ],
  "kickMessage": "Custom kick message"
}
```