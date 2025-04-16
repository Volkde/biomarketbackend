<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Confirm your email</title>
</head>
<body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 40px;">
<div style="max-width: 600px; margin: auto; background: #ffffff; border-radius: 8px; padding: 30px; box-shadow: 0 2px 10px rgba(0,0,0,0.1);">
    <h2 style="color: #333;">Welcome, ${username}!</h2>
    <p style="font-size: 16px; color: #555;">Thank you for registering at <strong>BioMarketplace</strong>.</p>
    <p style="font-size: 16px; color: #555;">To confirm your email address and activate your account, click the button below:</p>

    <p style="text-align: center; margin: 30px 0;">
        <a href="${confirmLink}" style="display: inline-block; padding: 12px 24px; background-color: #28a745; color: #fff; text-decoration: none; border-radius: 5px; font-weight: bold;">
            Confirm Email
        </a>
    </p>

    <p style="font-size: 14px; color: #999;">If the button doesn't work, you can also copy and paste this link into your browser:</p>
    <p style="font-size: 14px; word-break: break-all;"><a href="${confirmLink}">${confirmLink}</a></p>

    <hr style="margin: 40px 0; border: none; border-top: 1px solid #eee;">
    <p style="font-size: 12px; color: #aaa;">If you didn’t request this, just ignore this email.</p>
</div>
</body>
</html>
